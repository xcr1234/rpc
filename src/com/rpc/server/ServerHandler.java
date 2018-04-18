package com.rpc.server;

import com.rpc.io.meta.*;

public interface ServerHandler {
    void handle(ResultInfo resultInfo, RpcConnection connection
            , Version clientVersion, InvocationInfo invocationInfo, Auth auth);

    void setServer(RpcServer server);
}
