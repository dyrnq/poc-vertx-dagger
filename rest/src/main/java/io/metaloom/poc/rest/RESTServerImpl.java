package io.metaloom.poc.rest;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import io.metaloom.poc.server.RESTServer;
import io.reactivex.rxjava3.core.Completable;
import io.vertx.rxjava3.core.http.HttpServer;
import io.vertx.rxjava3.ext.web.Router;

@Singleton
public class RESTServerImpl implements RESTServer {

	private final HttpServer rxHttpServer;
	private final Router rxRouter;

	@Inject
	public RESTServerImpl(HttpServer rxHttpServer, Provider<Router> rxRouterProvider) {
		this.rxHttpServer = rxHttpServer;
		this.rxRouter = rxRouterProvider.get();
	}

	@Override
	public Completable start() {
		rxRouter.route("/hello").handler(rc -> {
			rc.response().end("world");
		});
		return rxHttpServer
			.requestHandler(rxRouter)
			.rxListen()
			.ignoreElement();
	}

	@Override
	public Completable stop() {
		return rxHttpServer.rxClose();
	}

}
