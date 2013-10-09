package kz.greetgo.smartgwt.share.remote;

import com.google.gwt.user.client.rpc.RemoteService;

public interface ActionService<A, R> extends RemoteService {
  R invoke(A action) throws Exception;
}
