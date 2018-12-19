package com.afgrey.solarlinx.user.handlers;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class UserSignUpHandler implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext event) {
        //@TODO create webclient to call keycloak api and initialise it with details from the config object

       //@TODO call keycloak and regiter new user
    }
}
