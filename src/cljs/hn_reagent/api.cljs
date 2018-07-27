(ns hn-reagent.api
    (:require-macros [cljs.core.async.macros :refer [go]])
    (:require [cljs-http.client :as http]
              [cljs.core.async :refer [<! >! chan]]))

(def api-url "https://hacker-news.firebaseio.com/v0/")


(defn get-top-stories [ch]
    (go (let [response (<! (http/get  (str api-url "topstories" ".json")
                                 {:with-credentials? false}))]
    ;   (prn (:status response))
      (doseq [id (seq (take 10 (:body response)))]
        (go (let [response (<! (http/get  (str api-url "item/" id ".json")
                                 {:with-credentials? false}))]
                
           (>! ch (:body response))
        ))))
))

