package edu.pdx.cs410J.pkadam;

import edu.pdx.cs410J.web.HttpRequestHelper;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import static javax.swing.text.html.FormSubmitEvent.MethodType.POST;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

/**
 * A unit test for the {@link AirlineServlet}.  It uses mockito to
 * provide mock http requests and responses.
 */
public class AirlineServletTest {

  @Test
  public void addOneFlight() throws ServletException, IOException {
    AirlineServlet servlet = new AirlineServlet();

    HttpServletResponse response = makeRequestOfServlet(HttpVerb.POST, servlet,"emirates", "111", "PDX", "07/19/2020 1:02 pm", "ORD", "07/19/2020 6:22 pm");
    verify(response).setStatus(HttpServletResponse.SC_OK);

    HttpServletResponse response1 = makeGETRequestOfServlet(HttpVerb.GET, servlet,"emirates", "PDX", "ORD");
    verify(response1).setStatus(HttpServletResponse.SC_OK);
  }

  private HttpServletResponse makeRequestOfServlet(HttpVerb verb, AirlineServlet servlet, String airlineName, String fnum, String src, String depart, String dest, String arrv) throws IOException, ServletException {
    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter("airline")).thenReturn(airlineName);
    when(request.getParameter("flightnum")).thenReturn(fnum);
    when(request.getParameter("src")).thenReturn(src);
    when(request.getParameter("depart")).thenReturn(depart);
    when(request.getParameter("dest")).thenReturn(dest);
    when(request.getParameter("arrv")).thenReturn(arrv);

    HttpServletResponse response = mock(HttpServletResponse.class);
    PrintWriter pw = mock(PrintWriter.class);

    when(response.getWriter()).thenReturn(pw);

    switch (verb) {
      case GET:
        servlet.doGet(request, response);
        break;
      case POST:
        servlet.doPost(request, response);
        break;
      default:
        throw new UnsupportedOperationException("" + verb);
    }
    return response;
  }

  private HttpServletResponse makeGETRequestOfServlet(HttpVerb verb, AirlineServlet servlet, String airlineName, String src, String dest) throws IOException, ServletException {
    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter("name")).thenReturn(airlineName);
    when(request.getParameter("src")).thenReturn(src);
    when(request.getParameter("dest")).thenReturn(dest);

    HttpServletResponse response = mock(HttpServletResponse.class);
    PrintWriter pw = mock(PrintWriter.class);

    when(response.getWriter()).thenReturn(pw);

    switch (verb) {
      case GET:
        servlet.doGet(request, response);
        break;
      case POST:
        servlet.doPost(request, response);
        break;
      default:
        throw new UnsupportedOperationException("" + verb);
    }
    return response;
  }

  private enum HttpVerb {
    POST, GET;
  }
}
