package uk.andrewhook;

import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uk.andrewhook.restapi.Controller;
import uk.andrewhook.restapi.HTTPResponse;
import uk.andrewhook.restapi.ResponseMessage;

import com.ibm.xsp.webapp.DesignerFacesServlet;
import com.sun.xml.internal.ws.util.StringUtils;

public class APIServlet extends DesignerFacesServlet {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
		HttpServletRequest req = (HttpServletRequest) servletRequest;
		HttpServletResponse res = (HttpServletResponse) servletResponse;
		ServletOutputStream out = res.getOutputStream();
		FacesContext facesContext = this.getFacesContext(req, res);

		try {

			Map<Object, Object> applicationScope = facesContext.getExternalContext().getApplicationMap();

			Map<Object, Object> sessionScope = facesContext.getExternalContext().getSessionMap();

			// This is where routing should be performed.
			String contextPath = req.getContextPath();
			String uri = req.getRequestURI();
			String reqPath = req.getPathInfo();
			List<String> parts = Arrays.asList(reqPath.split("/(.*?)"));

			if (parts.size() < 4) {
				throw new Exception("Entity not specified.");
			}

			String entity = parts.get(3);
			String controllerName = StringUtils.capitalize(entity) + "Controller";
			String identifier = null;
			if (parts.size() > 4) {
				identifier = parts.get(4);
			}

			Class<?> clazz = Class.forName("com.app.controllers." + controllerName);
			Constructor<?> constructor = clazz.getConstructor();
			Controller instance = (Controller) constructor.newInstance();
			ServletContext servletContext = this.getServletConfig().getServletContext();

			if (req.getMethod().equalsIgnoreCase("GET") && identifier != null) {
				HTTPResponse response = instance.getSingle(req, facesContext, servletContext, identifier);
				res.setStatus(response.getStatusCode());
				res.setContentType(response.getContentType());
				out.print(response.getBody());
			} else if (req.getMethod().equalsIgnoreCase("GET")) {
				HTTPResponse response = instance.getIndex(req, facesContext, servletContext);
				res.setStatus(response.getStatusCode());
				res.setContentType(response.getContentType());
				out.print(response.getBody());
			} else if (req.getMethod().equalsIgnoreCase("POST")) {
				HTTPResponse response = instance.postCreate(req, facesContext, servletContext);
				res.setStatus(response.getStatusCode());
				res.setContentType(response.getContentType());
				out.print(response.getBody());
			} else if (req.getMethod().equalsIgnoreCase("PUT") && identifier != null) {
				HTTPResponse response = instance.putUpdate(req, facesContext, servletContext, identifier);
				res.setStatus(response.getStatusCode());
				res.setContentType(response.getContentType());
				out.print(response.getBody());
			} else if (req.getMethod().equalsIgnoreCase("DELETE") && identifier != null) {
				HTTPResponse response = instance.deleteDestroy(req, facesContext, servletContext, identifier);
				res.setStatus(response.getStatusCode());
				res.setContentType(response.getContentType());
				out.print(response.getBody());
			} else {
				throw new Exception("Controller method not found. Method was " + req.getMethod());
			}

		} catch (Exception e) {
			ResponseMessage error = new ResponseMessage();
			error.setMessage("Error: " + e.getMessage());
			error.setStatusCode(400);

			HTTPResponse response = new HTTPResponse().setStatusCode(400).setContentType("application/json").setBody(error.asJson());
			res.setStatus(response.getStatusCode());
			res.setContentType(response.getContentType());
			out.print(response.getBody());
			e.printStackTrace(new PrintStream(out));
			e.printStackTrace();
		} finally {
			out.close();
			if (facesContext != null) {
				facesContext.responseComplete();
				facesContext.release();
			}
		}
	}

	/**
	 * @return FacesContext currentInstance
	 */
	public static FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}
}