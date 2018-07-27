(ns hn-reagent.component.story
    (:require [reagent.core :as r]))

(defn story-item [story]
    [:div
        [:a.title {:href (:url story)}   (:title story)]
        [:span (:by story)]
        [:span (:score story)] ]
    )

(defn story-list [stories]
    [:div
        (for [story (sort-by :score > stories)]
            ^{:key (:id story)} [story-item story])
        ]
    )