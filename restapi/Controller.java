package uk.andrewhook.restapi;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @author ahook
 * 
 */

public interface Controller {
	public HTTPResponse getIndex(HttpServletRequest req, FacesContext facesContext, ServletContext servletContext);

	public HTTPResponse getSingle(HttpServletRequest req, FacesContext facesContext, ServletContext servletContext, String identifier);

	public HTTPResponse postCreate(HttpServletRequest req, FacesContext facesContext, ServletContext servletContext);

	public HTTPResponse putUpdate(HttpServletRequest req, FacesContext facesContext, ServletContext servletContext, String identifier);

	public HTTPResponse deleteDestroy(HttpServletRequest req, FacesContext facesContext, ServletContext servletContext, String identifier);
}
