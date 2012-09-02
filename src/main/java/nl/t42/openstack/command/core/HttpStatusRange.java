package nl.t42.openstack.command.core;

public class HttpStatusRange implements HttpStatusMatcher {

    private int from;
    private int to;

    public HttpStatusRange(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public boolean matches(int statusCode) {
        return statusCode >= this.from && statusCode <= this.to;
    }
}
