(ns cljdoc.server.log
  (:refer-clojure :exclude [read spit])
  (:require [clojure.tools.logging :as log]
            [clojure.java.io :as io]))

(def prefix "/tmp/cljdoc-builds/")

(defn exists? [build-id]
  (.exists (io/file prefix build-id)))

(defn read [build-id]
  (slurp (io/file prefix build-id)))

(defn spit [build-id msg]
  (doto (io/file "/tmp/cljdoc-builds/" build-id)
    (io/make-parents)
    (spit msg :append true)))

(defn log [build-id msg]
  (log/info "Build log: " msg)
  (spit build-id msg))
