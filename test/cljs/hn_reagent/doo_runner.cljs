(ns hn-reagent.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [hn-reagent.core-test]))

(doo-tests 'hn-reagent.core-test)
