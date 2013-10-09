package kz.greetgo.smartgwt.share.remote;

import kz.greetgo.smartgwt.share.model.PageAction;
import kz.greetgo.smartgwt.share.model.PageResult;

public interface PagingService<A, R> extends ActionService<PageAction<A>, PageResult<R>> {}
