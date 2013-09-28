(ns rts.core
  (:require rts.gl))

(defn -main [& args]
  (do
    (rts.gl/initGL)
    (println "testing")))
