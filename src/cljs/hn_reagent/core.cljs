(ns hn-reagent.core
    (:require-macros [cljs.core.async.macros :refer [go-loop]])
    (:require [reagent.core :as reagent :refer [atom]]
              [secretary.core :as secretary :include-macros true]
              [accountant.core :as accountant]
              [cljs.core.async :refer [<! >! chan]]
              [hn-reagent.component.story :as story]
              [hn-reagent.api :as api]))

;; -------------------------
;; Views

(defonce app-state (atom {:stories []}))

(defonce new-story-channel (chan))

(defn add-story [story]
  (swap! app-state assoc-in  [:stories] (conj (:stories @app-state) story)))

(defn new-story-listener []
  (go-loop [] (let [story  (<! new-story-channel)] 
    (add-story story)
    (recur))))

(defn load-stories []
    (api/get-top-stories new-story-channel))

(load-stories)

(defn home-page []
  :component-did-mount  load-stories
   :reagent-render [:div.container [:h2 "Welcome to hn-reagent"]
   [story/story-list (:stories @app-state)]])

(defn about-page []
  [:div [:h2 "About hn-reagent"]
   [:div [:a {:href "/"} "go to the home page"]]])

;; -------------------------
;; Routes

(defonce page (atom #'home-page))

(defn current-page []
  [:div [@page]])

(secretary/defroute "/" []
  (reset! page #'home-page))

(secretary/defroute "/about" []
  (reset! page #'about-page))

;; -------------------------
;; Initialize app

(defn mount-root []
  (reagent/render [current-page] (.getElementById js/document "app")))

(defn init! []
  (accountant/configure-navigation!
    {:nav-handler
     (fn [path]
       (secretary/dispatch! path))
     :path-exists?
     (fn [path]
       (secretary/locate-route path))})
  (accountant/dispatch-current!)
  (mount-root)
  (new-story-listener))
