package org.pgist.renderkit;

import java.io.IOException;
import java.util.Map;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.fileupload.FileItem;
import org.apache.myfaces.component.html.util.MultipartRequestWrapper;
import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.apache.myfaces.custom.fileupload.UploadedFileDefaultFileImpl;
import org.pgist.component.UIAction;
import org.pgist.model.Node;
import org.pgist.model.Tree;


/**
 * Renderer for Focus tag
 * @author kenny
 *
 */
public class DoFocusRenderer extends BaseRenderer {


    public void decode(FacesContext context, UIComponent component) throws NullPointerException {
        String prefix = (String) component.getAttributes().get("_PREFIX");
        String paramName = getHiddenFieldName(context, component);
        String clientId = component.getClientId(context);
        String varPrefix = clientId.replace(':', '_');
        
        ServletRequest multipartRequest = (ServletRequest)context.getExternalContext().getRequest();
        while (multipartRequest != null && !(multipartRequest instanceof MultipartRequestWrapper)) {
            if (multipartRequest instanceof HttpServletRequestWrapper) {
                multipartRequest = ((HttpServletRequestWrapper)multipartRequest).getRequest();
            } else {
                multipartRequest = null;
            }
        }//while

        if (multipartRequest != null) {
            MultipartRequestWrapper mpReq = (MultipartRequestWrapper)multipartRequest;
            
            Map map = mpReq.getParameterMap();
            
            String value = null;
            
            Object temp = map.get(paramName);
            if (temp instanceof String[]) {
                value = ((String[]) temp)[0];
            } else if (temp instanceof String) {
                value = (String) temp;
            }
            
            if(value == null || value.equals("") || !clientId.equals(value)) return;
            
            String treeId = ((String[]) map.get(prefix+"_treeId"))[0];
            String nodeId = ((String[]) map.get(prefix+"_nodeId"))[0];
            if (treeId!=null && !"".equals(treeId) && nodeId!=null && !"".equals(nodeId)) {
                UIAction compt = (UIAction) component;
                compt.getParams().put("treeId", treeId);
                compt.getParams().put("nodeId", nodeId);
                String punctuate = ((String[]) map.get(prefix+"_punctuate"))[0];
                String cttType = ((String[]) map.get(prefix+"_contenttype"))[0];
                compt.getParams().put("punctuate", punctuate);
                compt.getParams().put("cttType", cttType);
                System.out.println("---> "+cttType);
                if ("0".equals(cttType)) {//text
                    String cttText = ((String[]) map.get(varPrefix+"_text"))[0];
                    compt.getParams().put("cttText", cttText);
                } else if ("1".equals(cttType)) {//image
                    FileItem fileItem = mpReq.getFileItem(varPrefix+"_image");
                    if (fileItem != null) {
                        try{
                            UploadedFile upFile;
                            upFile = new UploadedFileDefaultFileImpl(fileItem);
                            compt.getParams().put("cttImage", upFile);
                        }catch(IOException ioe){
                            ioe.printStackTrace();
                        }
                    }
                } else if ("2".equals(cttType)) {//link
                    String cttText = ((String[]) map.get(varPrefix+"_link"))[0];
                    compt.getParams().put("cttLink", cttText);
                }
                ActionEvent event = new ActionEvent(component);
                component.queueEvent(event);
            }
        }
    }//decode()
    
    
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        super.encodeBegin(context, component);
        
        Tree tree = (Tree) component.getValueBinding("tree").getValue(context);
        Node node = (Node) component.getValueBinding("node").getValue(context);
        if (node==null) node = tree.getRoot();
        
        String clientId = component.getClientId(context);
        String formId = getMyForm(context, component).getClientId(context);
        String prefix = (String) component.getAttributes().get("_PREFIX");
        String varPrefix = clientId.replace(':', '_');
        
        ResponseWriter writer = context.getResponseWriter();
        
        ViewHandler handler = context.getApplication().getViewHandler();
        ExternalContext cntxt = context.getExternalContext();
        
        String[] punctUnselected = new String[] {
                cntxt.encodeResourceURL(handler.getResourceURL(context, "/images/dot.png")),
                cntxt.encodeResourceURL(handler.getResourceURL(context, "/images/question.png")),
                cntxt.encodeResourceURL(handler.getResourceURL(context, "/images/exclam.png"))
        };
        String[] punctSelected = new String[] {
                cntxt.encodeResourceURL(handler.getResourceURL(context, "/images/dot1.png")),
                cntxt.encodeResourceURL(handler.getResourceURL(context, "/images/question1.png")),
                cntxt.encodeResourceURL(handler.getResourceURL(context, "/images/exclam1.png"))
        };
        
        String[] cttUnselected = new String[] {
                cntxt.encodeResourceURL(handler.getResourceURL(context, "/images/ctt_text.png")),
                cntxt.encodeResourceURL(handler.getResourceURL(context, "/images/ctt_image.png")),
                cntxt.encodeResourceURL(handler.getResourceURL(context, "/images/ctt_link.png"))
        };
        String[] cttSelected = new String[] {
                cntxt.encodeResourceURL(handler.getResourceURL(context, "/images/ctt_text1.png")),
                cntxt.encodeResourceURL(handler.getResourceURL(context, "/images/ctt_image1.png")),
                cntxt.encodeResourceURL(handler.getResourceURL(context, "/images/ctt_link1.png"))
        };
        String[] cttPanel = new String[] {
                clientId+"_panel_text",
                clientId+"_panel_image",
                clientId+"_panel_link"
        };
        
        try {
            writer.write("<script language=\"JavaScript\">\n");
            writer.write("var clientId='"+clientId+"';");
            writer.write("var varPrefix='"+varPrefix+"';");
            writer.write("var cttId=[ clientId+'_text', clientId+'_image', clientId+'_link' ];\n");
            writer.write("var cttName=[ varPrefix+'_text', varPrefix+'_image', varPrefix+'_link' ];\n");
            writer.write("var cttSelected=[");
            for (int i=0; i<cttSelected.length; i++) {
                if (i>0) writer.write(",");
                writer.write("'"+cttSelected[i]+"'");
            }//for i
            writer.write("];");
            writer.write("var cttUnselected=[");
            for (int i=0; i<cttUnselected.length; i++) {
                if (i>0) writer.write(",");
                writer.write("'"+cttUnselected[i]+"'");
            }//for i
            writer.write("];");
            writer.write("var cttPanel=[");
            for (int i=0; i<cttPanel.length; i++) {
                if (i>0) writer.write(",");
                writer.write("'"+cttPanel[i]+"'");
            }//for i
            writer.write("];");
            
            writer.write("function "+varPrefix+"_clickPunctuate(n) {\n");
            writer.write("m = document.forms['" + formId+"']['"+prefix+"_punctuate'].value;\n");
            writer.write("if (m==1) { $('"+clientId+"_dot').src='"+punctUnselected[0]+"'; }");
            writer.write("else if (m==2) { $('"+clientId+"_question').src='"+punctUnselected[1]+"'; }");
            writer.write("else if (m==3) { $('"+clientId+"_exclam').src='"+punctUnselected[2]+"'; }");
            writer.write("if (n==1) { $('"+clientId+"_dot').src='"+punctSelected[0]+"'; }");
            writer.write("else if (n==2) { $('"+clientId+"_question').src='"+punctSelected[1]+"'; }");
            writer.write("else if (n==3) { $('"+clientId+"_exclam').src='"+punctSelected[2]+"'; }");
            writer.write("document.forms['" + formId+"']['"+prefix+"_punctuate'].value=n;\n");
            writer.write("}\n");
            
            writer.write("function "+varPrefix+"_clickContentType(n) {\n");
            writer.write("m = document.forms['" + formId+"']['"+prefix+"_contenttype'].value;\n");
            writer.write("if (m!=-1) { $(cttId[m]).src=cttUnselected[m]; $(cttPanel[m]).style.display='none'; }");
            writer.write("$(cttId[n]).src=cttSelected[n];");
            writer.write("$(cttPanel[n]).style.display='inline';");
            writer.write("document.forms['" + formId+"']['"+prefix+"_contenttype'].value=n;\n");
            writer.write("}\n");
            
            String paramName = getHiddenFieldName(context, component);

            writer.write("function "+varPrefix+"_submitContent() {\n");
            writer.write("n = document.forms['" + formId+"']['"+prefix+"_contenttype'].value;\n");
            writer.write("document.forms['" + formId+"']['"+paramName+"'].value='"+clientId+"';\n");
            writer.write("document.forms['" + formId+"'].submit();\n");
            writer.write("}\n");
            
            writer.write("</script>\n");
            
            writer.startElement("table", null);
            writer.writeAttribute("cellpadding", "0", null);
            writer.writeAttribute("cellspacing", "0", null);
            writer.writeAttribute("border", "0", null);
            writer.writeAttribute("width", "100%", null);
            
            writer.startElement("tr", null);
            writer.startElement("td", null);
            writer.writeAttribute("width", "100%", null);
            
            writer.startElement("table", null);
            writer.writeAttribute("cellpadding", "0", null);
            writer.writeAttribute("cellspacing", "0", null);
            writer.writeAttribute("border", "0", null);
            writer.writeAttribute("width", "100%", null);

            writer.startElement("tr", null);
            writer.startElement("td", null);

            writer.startElement("img", null);
            writer.writeAttribute("id", clientId+"_dot", null);
            writer.writeAttribute("src", punctSelected[0], null);
            writer.writeAttribute("border", "0", null);
            writer.writeAttribute("width", "16", null);
            writer.writeAttribute("height", "16", null);
            writer.writeAttribute("onClick", varPrefix+"_clickPunctuate(1);", null);
            writer.endElement("img");
            
            writer.startElement("img", null);
            writer.writeAttribute("id", clientId+"_question", null);
            writer.writeAttribute("src", punctUnselected[1], null);
            writer.writeAttribute("border", "0", null);
            writer.writeAttribute("width", "16", null);
            writer.writeAttribute("height", "16", null);
            writer.writeAttribute("onClick", varPrefix+"_clickPunctuate(2);", null);
            writer.endElement("img");
            
            writer.startElement("img", null);
            writer.writeAttribute("id", clientId+"_exclam", null);
            writer.writeAttribute("src", punctUnselected[2], null);
            writer.writeAttribute("border", "0", null);
            writer.writeAttribute("width", "16", null);
            writer.writeAttribute("height", "16", null);
            writer.writeAttribute("onClick", varPrefix+"_clickPunctuate(3);", null);
            writer.endElement("img");
            
            writer.endElement("td");

            writer.startElement("td", null);
            writer.writeAttribute("alight", "right", null);
            
            //content types
            writer.startElement("img", null);
            writer.writeAttribute("id", clientId+"_text", null);
            writer.writeAttribute("src", cttUnselected[0], null);
            writer.writeAttribute("border", "0", null);
            writer.writeAttribute("width", "16", null);
            writer.writeAttribute("height", "16", null);
            writer.writeAttribute("onClick", varPrefix+"_clickContentType(0);", null);
            writer.endElement("img");
            writer.startElement("img", null);
            writer.writeAttribute("id", clientId+"_image", null);
            writer.writeAttribute("src", cttUnselected[1], null);
            writer.writeAttribute("border", "0", null);
            writer.writeAttribute("width", "16", null);
            writer.writeAttribute("height", "16", null);
            writer.writeAttribute("onClick", varPrefix+"_clickContentType(1);", null);
            writer.endElement("img");
            writer.startElement("img", null);
            writer.writeAttribute("id", clientId+"_link", null);
            writer.writeAttribute("src", cttUnselected[2], null);
            writer.writeAttribute("border", "0", null);
            writer.writeAttribute("width", "16", null);
            writer.writeAttribute("height", "16", null);
            writer.writeAttribute("onClick", varPrefix+"_clickContentType(2);", null);
            writer.endElement("img");
            
            writer.endElement("td");
            writer.endElement("tr");
            writer.endElement("table");

            writer.endElement("td");
            writer.endElement("tr");

            writer.startElement("tr", null);
            writer.startElement("td", null);

            //panel
            for (int i=0; i<cttPanel.length; i++) {
                writer.startElement("table", null);
                writer.writeAttribute("id", cttPanel[i], null);
                writer.writeAttribute("style", "display:none;", null);
                writer.writeAttribute("cellpadding", "0", null);
                writer.writeAttribute("cellspacing", "0", null);
                writer.writeAttribute("border", "0", null);
                writer.writeAttribute("width", "100%", null);
                
                writePanel(writer, varPrefix, i);
                
                writer.endElement("table");
            }//for i

            writer.endElement("td");
            writer.endElement("tr");

            writer.endElement("table");
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }//encodeBegin()


    private void writePanel(ResponseWriter writer, String varPrefix, int i) throws Exception {
        writer.startElement("tr", null);
        writer.startElement("td", null);
        switch(i) {
            case 0:
                writer.writeText("Input your opinion:", null);
                writer.endElement("td");
                writer.endElement("tr");
                writer.startElement("tr", null);
                writer.startElement("td", null);
                writer.startElement("textarea", null);
                writer.writeAttribute("name", varPrefix+"_text", null);
                writer.writeAttribute("cols", "30", null);
                writer.writeAttribute("rows", "8", null);
                writer.endElement("textarea");
                break;
            case 1:
                writer.writeText("Upload an image:", null);
                writer.endElement("td");
                writer.endElement("tr");
                writer.startElement("tr", null);
                writer.startElement("td", null);
                writer.startElement("input", null);
                writer.writeAttribute("type", "file", null);
                writer.writeAttribute("name", varPrefix+"_image", null);
                writer.endElement("input");
                break;
            case 2:
                writer.writeText("Input a link:", null);
                writer.endElement("td");
                writer.endElement("tr");
                writer.startElement("tr", null);
                writer.startElement("td", null);
                writer.startElement("input", null);
                writer.writeAttribute("type", "text", null);
                writer.writeAttribute("name", varPrefix+"_link", null);
                writer.endElement("input");
                break;
        }//switch
        writer.endElement("td");
        writer.endElement("tr");
        writer.startElement("tr", null);
        writer.startElement("td", null);
        writer.startElement("input", null);
        writer.writeAttribute("type", "button", null);
        writer.writeAttribute("value", "submit", null);
        writer.writeAttribute("onClick", varPrefix+"_submitContent();", null);
        writer.endElement("input");
        writer.endElement("td");
        writer.endElement("tr");
    }//writePanel()


}//class DoFocusRenderer
