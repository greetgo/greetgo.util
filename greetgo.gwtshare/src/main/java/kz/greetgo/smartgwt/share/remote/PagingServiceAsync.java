package kz.greetgo.smartgwt.share.remote;

import kz.greetgo.smartgwt.share.model.PageAction;
import kz.greetgo.smartgwt.share.model.PageResult;

public interface PagingServiceAsync<A, R> extends ActionServiceAsync<PageAction<A>, PageResult<R>> {}
