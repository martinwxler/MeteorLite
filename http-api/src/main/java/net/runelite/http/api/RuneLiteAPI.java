/*
 * Copyright (c) 2017, Adam <Adam@sigterm.info>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.http.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.awt.Color;
import java.io.IOException;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import net.runelite.http.api.gson.ColorTypeAdapter;
import net.runelite.http.api.gson.IllegalReflectionExclusion;
import net.runelite.http.api.gson.InstantTypeAdapter;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.sponge.util.Logger;

public class RuneLiteAPI {

  public static final OkHttpClient CLIENT;
  public static final Gson GSON;
  public static final MediaType JSON = MediaType.parse("application/json");
  private static final Logger logger = new Logger("");
  private static final String BASE = "https://api.runelite.net";
  private static final String WSBASE = "https://api.runelite.net/ws";
  private static final String STATICBASE = "https://static.runelite.net";
  private static final String METEOR_SESSION = "https://session.openosrs.dev";
  public static String userAgent;
  private static String version;

  static {
    version = "1.7.19";
    userAgent = "RuneLite/" + version + "-";

    CLIENT = new OkHttpClient.Builder()
        .pingInterval(30, TimeUnit.SECONDS)
        .addNetworkInterceptor(new Interceptor() {

          @Override
          public Response intercept(Chain chain) throws IOException {
            Request userAgentRequest = chain.request()
                .newBuilder()
                .header("User-Agent", userAgent)
                .build();
            return chain.proceed(userAgentRequest);
          }
        })
        .build();

    GsonBuilder gsonBuilder = new GsonBuilder();

    gsonBuilder
        .registerTypeAdapter(Instant.class, new InstantTypeAdapter())
        .registerTypeAdapter(Color.class, new ColorTypeAdapter());

    boolean assertionsEnabled = false;
    assert assertionsEnabled = true;
    if (assertionsEnabled) {
      IllegalReflectionExclusion jbe = new IllegalReflectionExclusion();
      gsonBuilder.addSerializationExclusionStrategy(jbe);
      gsonBuilder.addDeserializationExclusionStrategy(jbe);
    }

    GSON = gsonBuilder.create();
  }

  public static HttpUrl getSessionBase() {
    return HttpUrl.parse(METEOR_SESSION);
  }

  public static HttpUrl getApiBase() {
    final String prop = System.getProperty("runelite.http-service.url");

    if (prop != null && !prop.isEmpty()) {
      return HttpUrl.parse(prop);
    }

    return HttpUrl.parse(BASE + "/runelite-" + getVersion());
  }

  public static HttpUrl getStaticBase() {
    final String prop = System.getProperty("runelite.static.url");

    if (prop != null && !prop.isEmpty()) {
      return HttpUrl.parse(prop);
    }

    return HttpUrl.parse(STATICBASE);
  }

  public static HttpUrl getWsEndpoint() {
    final String prop = System.getProperty("runelite.ws.url");

    if (prop != null && !prop.isEmpty()) {
      return HttpUrl.parse(prop);
    }

    return HttpUrl.parse(WSBASE);
  }

  public static String getVersion() {
    return version;
  }

  public static void setVersion(String version) {
    RuneLiteAPI.version = version;
  }

}
