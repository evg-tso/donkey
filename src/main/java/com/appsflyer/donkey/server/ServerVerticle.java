/*
 * Copyright 2020-2021 AppsFlyer
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.appsflyer.donkey.server;

import com.appsflyer.donkey.server.handler.ErrorHandler;
import com.appsflyer.donkey.server.router.RouterFactoryImpl;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ServerVerticle extends AbstractVerticle {
  
  private final ServerConfig config;
  
  @Override
  public void start(Promise<Void> promise) {
    vertx.createHttpServer(config.serverOptions())
         .requestHandler(createRouter())
         .listen(res -> {
           if (res.failed()) {
             logger.error(res.cause().getMessage(), res.cause());
             promise.fail(res.cause());
           } else {
             promise.complete();
           }
         });
  }
  
  private Router createRouter() {
    var router = RouterFactoryImpl.create(vertx, config.routeList())
                                  .withRouteCreator(config.routeCreatorFactory());
    ErrorHandler<?> errorHandler = config.errorHandler();
    if (errorHandler != null) {
      errorHandler.forEach(router::errorHandler);
    }
    return router;
  }
}
