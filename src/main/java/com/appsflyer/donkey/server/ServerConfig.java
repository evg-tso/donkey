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
import com.appsflyer.donkey.server.route.RouteCreatorFactory;
import com.appsflyer.donkey.server.route.RouteList;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerOptions;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * Configuration object used for initializing a {@link Server}.
 * Use {@link #builder()} to create instances.
 */
@Builder
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class ServerConfig {
  
  @Getter
  @NonNull
  private Vertx vertx;
  
  @Getter(AccessLevel.PACKAGE)
  @NonNull
  private HttpServerOptions serverOptions;
  
  @Getter
  @NonNull
  private RouteCreatorFactory routeCreatorFactory;
  
  @Getter
  @NonNull
  private RouteList routeList;
  
  @Getter(AccessLevel.PACKAGE)
  private ErrorHandler<?> errorHandler;
  
  @Getter
  private int instances;
  
  @Getter(AccessLevel.PACKAGE)
  private boolean addDateHeader;
  
  @Getter(AccessLevel.PACKAGE)
  private boolean addContentTypeHeader;
  
  @Getter(AccessLevel.PACKAGE)
  private boolean addServerHeader;
  
  public static class ServerConfigBuilder {
    
    public ServerConfig build() {
      if (instances < 1) {
        throw new IllegalArgumentException("Number of instances must be greater than 0");
      }
      
      return new ServerConfig(vertx, serverOptions, routeCreatorFactory, routeList, errorHandler, instances, addDateHeader, addContentTypeHeader, addServerHeader);
    }
  }
}
