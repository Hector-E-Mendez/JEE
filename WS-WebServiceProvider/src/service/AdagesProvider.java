package service;

import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.StringReader;

import javax.annotation.Resource;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.BindingType;
import javax.xml.ws.Provider;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.WebServiceProvider;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.http.HTTPBinding;
import javax.xml.ws.http.HTTPException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.xml.sax.InputSource;

import domain.Adage;

@WebServiceProvider
@ServiceMode(javax.xml.ws.Service.Mode.MESSAGE)
@BindingType(HTTPBinding.HTTP_BINDING)
public class AdagesProvider implements Provider<Source>{
	
	@Resource
	protected WebServiceContext wctx;
	
	public AdagesProvider(){}
	
	@Override
	public Source invoke(Source request) {
		// TODO Auto-generated method stub
		if (wctx == null)
			throw new RuntimeException("Inject failed on wctx.");
		
		MessageContext mctx = wctx.getMessageContext();
		String httpVerb = (String) mctx.get(MessageContext.HTTP_REQUEST_METHOD);
		httpVerb = httpVerb.trim().toUpperCase();
		
		System.out.println(request);
		
		if (httpVerb.equals("GET")){
			return doGet(mctx);
		}
		else if (httpVerb.equals("POST")){
			return doPost(request);
		}
		else if (httpVerb.equals("PUT")){
			return doPut(request);
		}
		else if (httpVerb.equals("DELETE")){
			return doDelete(mctx);
		}
		else{
			throw new HTTPException(405);
		}
	}

	private Source doGet(MessageContext mctx){
		String qs = (String) mctx.get(MessageContext.QUERY_STRING);
		if (qs == null){
			return adagesXml();
		}
		else{
			int id = getId(qs);
			if (id < 0){
				throw new HTTPException(400);
			}
			Adage adage = Adages.find(id);
			if (adage == null){
				throw new HTTPException(404);
			}
			return adagesXml(adage);
		}
	}
	
	private Source doPost(Source request){
		if (request == null){
			throw new HTTPException(400);
		}
		InputSource in = toInputSource(request);
		String pattern = "//words/text()";
		String words = findElement(pattern, in);
		if (words == null){
			throw new HTTPException(400);
		}
		Adages.add(words);
		String msg = "The adage '" + words + "' has been created.";
		return toSource(toXml(msg));
	}
	
	private Source doPut(Source request){
		if (request == null){
			throw new HTTPException(400);
		}
		InputSource in = toInputSource(request);
		String pattern = "//words/text()";
		String words = findElement(pattern, in);
		if (words == null){
			throw new HTTPException(400);
		}
		String[] parts = words.split("!");
		if (parts[0].length() < 1 || parts[1].length() < 1){
			throw new HTTPException(400);
		}
		int id = -1;
		try{
			id = Integer.parseInt(parts[1].trim());
		}
		catch (Exception e){
			throw new HTTPException(400);
		}
		Adage adage = Adages.find(id);
		if (adage == null){
			throw new HTTPException(404);
		}
		adage.setWords(parts[0]);
		String msg = "Adage " + adage.getId() + " has been updated.";
		return toSource(toXml(msg));
	}
	
	private Source doDelete(MessageContext mctx){
		String qs = (String) mctx.get(MessageContext.QUERY_STRING);
		if (qs == null){
			throw new HTTPException(403);
		}
		else{
			int id = getId(qs);
			if (id < 0){
				throw new HTTPException(400);
			}
			Adage adage = Adages.find(id);
			if (adage == null){
				throw new HTTPException(404);
			}
			Adages.remove(adage);
			String msg = "Adage " + adage.getId() + " has been removed.";
			return toSource(toXml(msg));
		}
	}
	
	public int getId(String qs){
		int badId = -1;
		String[] parts = qs.split("=");
		if (!parts[0].toLowerCase().trim().equals("id")){
			return badId;
		}
		int goodId = badId;
		try{
			goodId = Integer.parseInt(parts[1].trim());
		}
		catch (Exception e){
			return badId;
		}
		return goodId;
	}
	
	private StreamSource adagesXml(){
		String str = toXml(Adages.getList());
		return toSource(str);
	}
	
	private StreamSource adagesXml(Adage adage){
		String str = toXml(adage);
		return toSource(str);
	}
	
	private String toXml(Object obj){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		XMLEncoder enc = new XMLEncoder(out);
		enc.writeObject(obj);
		enc.close();
		return out.toString();
	}
	
	private StreamSource toSource(String str){
		return new StreamSource(new StringReader(str));
	}
	
	private InputSource toInputSource(Source source){
		InputSource input = null;
		try{
			Transformer trans = TransformerFactory.newInstance().newTransformer();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			StreamResult result = new StreamResult(bos);
			trans.transform(source, result);
			input = new InputSource(new ByteArrayInputStream(bos.toByteArray()));
		}
		catch (Exception e){
			throw new HTTPException(500);
		}
		return input;
	}
	
	private String findElement(String expression, InputSource source){
		XPath xpath = XPathFactory.newInstance().newXPath();
		String retval = null;
		try{
			retval = (String) xpath.evaluate(expression, source, XPathConstants.STRING);
		}
		catch (Exception e){
			throw new HTTPException(400);
		}
		return retval;
	}
}
