package listeners;

import utils.RequestType;
import utils.ResponseStatus;

/**
 * Created by nalin on 1/2/16.
 */
public class ResponseArgs {

    public Object args;
    public ResponseStatus responseStatus;
    public RequestType requestType;

    public ResponseArgs(Object args, ResponseStatus responseStatus, RequestType requestType)
    {
        this.args = args;
        this.responseStatus = responseStatus;
        this.requestType = requestType;
    }
}
