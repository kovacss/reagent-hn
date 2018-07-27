(ns hn-reagent.prod
  (:require [hn-reagent.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
