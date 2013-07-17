package com.cloudmine.api;

import android.os.Handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class CurrentHandlerHavers {

    private Map<Object, List<HasHandler>> hasHandlers = new HashMap<Object, List<HasHandler>>();
    private static final Object defaultKey = new Object();
    public void addHasHandler(HasHandler hasHandler) {
        addHasHandler(defaultKey, hasHandler);
    }

    public void addHasHandler(Object tag, HasHandler hasHandler) {
        List<HasHandler> handlers = hasHandlers.get(tag);
        if(handlers == null) {
            handlers = new ArrayList<HasHandler>();
            hasHandlers.put(tag, handlers);
        }
        handlers.add(hasHandler);
    }

    public void setHandlers(Handler handler) {
        setHandlers(null, handler);
    }

    public void setHandlers(Object tag, Handler handler) {
        if(tag == null){
            for(List<HasHandler> hasHandlerList : hasHandlers.values()) {
                setHandlersValues(handler, hasHandlerList);
            }
        } else {
            List<HasHandler> handlers = hasHandlers.get(tag);
            if(handlers != null) {
                setHandlersValues(handler, handlers);
            }
        }
    }

    public void clearAllHandlers() {
        setHandlers(null, null);
    }

    public void clearHandlers(Object tag) {
        setHandlers(tag, null);
    }

    private void setHandlersValues(Handler handler, List<HasHandler> hasHandlerList) {
        for(int i = 0; i < hasHandlerList.size(); i++) {
            HasHandler hasHandler = hasHandlerList.get(i);
            hasHandler.setHandler(handler);
        }
    }
}
