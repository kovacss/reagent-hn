(ns ^:figwheel-no-load hn-reagent.dev
  (:require
    [hn-reagent.core :as core]
    [devtools.core :as devtools]))

(devtools/install!)

(enable-console-print!)

(core/init!)
