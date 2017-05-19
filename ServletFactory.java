package uk.andrewhook;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import com.ibm.designer.runtime.domino.adapter.ComponentModule;
import com.ibm.designer.runtime.domino.adapter.IServletFactory;
import com.ibm.designer.runtime.domino.adapter.ServletMatch;

/**
 * The factory (a provider) implements IServletFactory and creates two maps, for
 * key to package.class and key to servletname matching.
 */
public class ServletFactory implements IServletFactory {
	private static final Map<String, String> servletClasses = new HashMap<String, String>();
	private static final Map<String, String> servletNames = new HashMap<String, String>();
	private ComponentModule module;

	/**
	 * init adds the classes and servlet names, mapping to the same key.
	 */
	public void init(ComponentModule module) {

		// servletClasses.put("exhttpservlet",
		// "com.hello.servlets.ExampleHttpServlet");
		// servletNames.put("exhttpservlet", "Example HttpServlet");
		//
		// servletClasses.put("exdesignerfacesservlet",
		// "com.hello.servlets.ExampleDesignerFacesServlet");
		// servletNames.put("exdesignerfacesservlet",
		// "Example DesignerFaces Servlet");

		servletClasses.put("api", "uk.andrewhook.APIServlet");
		servletNames.put("api", "API Servlet");

		this.module = module;
	}

	/**
	 * The ServletMatch matches the path to the correctly identified servlet; by
	 * the routed key.
	 */
	public ServletMatch getServletMatch(String contextPath, String path)
			throws ServletException {
		try {
			String servletPath = "";
			// iterate the servletNames map
			Iterator<Map.Entry<String, String>> it = servletNames.entrySet()
					.iterator();
			while (it.hasNext()) {
				Map.Entry<String, String> pairs = it.next();
				if (path.contains("/" + pairs.getKey())) {
					String pathInfo = path;
					return new ServletMatch(getWidgetServlet(pairs.getKey()),
							servletPath, pathInfo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Servlet getWidgetServlet(String key) throws ServletException {
		return module.createServlet(servletClasses.get(key), servletNames
				.get(key), null);
	}
}