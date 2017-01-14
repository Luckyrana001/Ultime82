var Mint = function() {
    function t(t) {
        return "userAgent" in window.navigator ? -1 == window.navigator.userAgent.indexOf("Android") ? t : "object" == typeof t ? JSON.stringify(t) : void 0 === t || null === t ? "{}" : JSON.stringify({
            error: "Not a valid argument. Only JSON object allowed."
        }) : null
    }
    var n = function(t, n) {
            var i = t.stack.map(function(t) {
                return t.func + "@" + t.url + ":" + t.line
            }).join("\n");
            mintBridge.javascriptError(t.message, t.url, t.stack[0].line, i, n)
        },
        i = window.TraceKit.report,
        e = function() {
            var t = XMLHttpRequest.prototype.open,
                n = XMLHttpRequest.prototype.send;
            XMLHttpRequest.prototype.open = function(n, i, e, o, r) {
                this._method = n, this._url = i, t.call(this, n, i, e, o, r)
            }, XMLHttpRequest.prototype.send = function(t) {
                function i() {
                    if (4 == o.readyState) {
                        var t = (new Date).getTime(),
                            n = t - e,
                            i = {
                                method: r,
                                url: a,
                                latency: n.toString(),
                                httpStatusCode: o.status.toString(),
                                responseDataSize: o.responseText.length
                            };
                        mintBridge.logNetwork(i.method, i.url, i.latency, i.httpStatusCode, i.responseDataSize)
                    }
                }
                var e, o = this,
                    r = this._method,
                    a = this._url;
                e = (new Date).getTime(), this.addEventListener("readystatechange", i, !1), n.call(this, t)
            }
        },
        o = function() {
            {
                var t = window.location.pathname,
                    n = window.location.host;
                window.location.protocol
            }
            setTimeout(function() {
                var i = window.performance.timing.loadEventEnd - window.performance.timing.navigationStart,
                    e = window.performance.timing.domainLookupEnd - window.performance.timing.domainLookupStart,
                    o = window.performance.timing.domComplete - window.performance.timing.domLoading,
                    r = window.performance.timing.responseEnd - window.performance.timing.responseStart;
                mintBridge.logView(t, i, e, r, o, n, null)
            }, 0)
        };
    return e(), i.subscribe(function(t) {
        var i = "stack" === t.mode ? "true" : "false";
        n(t, i)
    }), window.onload = o, {
        errorLogger: i,
        initAndStartSession: function(t) {
            mintBridge.initAndStartSession(t)
        },
        initAndStartSessionHEC: function(url, token) {
            mintBridge.initAndStartSessionHEC(url, token)
        },       
        logEvent: function(n, i) {
            mintBridge.logEvent(n, t(i))
        },
        leaveBreadcrumb: function(t) {
            mintBridge.leaveBreadcrumb(t)
        },
        transactionStart: function(n, i) {
            mintBridge.transactionStart(n, t(i))
        },
        transactionStop: function(n, i) {
            mintBridge.transactionStop(n, t(i))
        },
        transactionCancel: function(n, i, e) {
            mintBridge.transactionCancel(n, i, t(e))
        },
        addExtraData: function(t, n) {
            mintBridge.addExtraData(t, n)
        },
        clearExtraData: function() {
            mintBridge.clearExtraData()
        },
        flush: function() {
            return mintBridge.flush()
        },
        startSession: function() {
            mintBridge.startSession()
        },
        closeSession: function() {
            mintBridge.closeSession()
        },
        logView: function(n, i) {
            mintBridge.logView(n, null, null, null, null, null, t(i))
        },
        setUserIdentifier: function(t) {
            mintBridge.setUserIdentifier(t)
        },
        setApplicationEnvironment: function(t) {
            mintBridge.setApplicationEnvironment(t)
        }        
    }
}();