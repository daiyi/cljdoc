(ns cljdoc.util)

(defn group-id [project]
  (or (if (symbol? project)
        (namespace project)
        (namespace (symbol project)))
      (name project)))

(defn artifact-id [project]
  (name project))

(defn codox-edn [project version]
  (str "codox-edn/" project "/" version "/codox.edn"))

(defn cljdoc-edn
  [project version]
  {:pre [(some? project) (string? version)]}
  (str "cljdoc-edn/" (group-id project) "/" (artifact-id project) "/" version "/cljdoc.edn"))

(defn local-jar-file [coordinate]
  ;; (jar-file '[org.martinklepsch/derivatives "0.2.0"])
  (->> (boot.pod/resolve-dependencies {:dependencies [coordinate]})
       (filter #(= coordinate (:dep %)))
       (first)
       :jar))

(defn remote-jar-file [[project version :as coordinate]]
  ;; Probably doesn't work with SNAPSHOTs
  (str "https://repo.clojars.org/"
       (group-id project) "/"
       (artifact-id project) "/"
       version "/"
       (artifact-id project) "-" version ".jar"))

(defn assert-first [[x & rest :as xs]]
  (if (empty? rest)
    x
    (throw (ex-info "Expected collection with one item, got multiple"
                    {:coll xs}))))
