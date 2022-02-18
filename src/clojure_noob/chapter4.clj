(ns clojure-noob.core
  (:gen-class))

(def filename "suspects.csv")

(slurp filename)

(def vamp-keys [:name :glitter-index])

(defn str->int
  [str]
  (Integer. str))

(def conversions {:name identity
                 :glitter-index str->int})


(defn convert
  [vamp-key value]
  ((get conversions vamp-key) value))

(defn parse
  "Convert a CSV into rows of columns"
  [string]
  (map #(clojure.string/split % #",")
       (clojure.string/split string #"\n")))


(parse (slurp filename))

(defn mapify
  "Return a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row)))
       rows))

(def suspect-list (->> filename
                       slurp
                       parse
                       mapify))


(map vector vamp-keys (first (parse (slurp filename))))

(defn glitter-filter
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter) records))

(def good-glittered (glitter-filter 3 tada))

(good-glittered)

;; Exercise 1.
(map :name good-glittered)

;; Exercise 2.

(defn append [suspect suspect-list]
  (conj suspect-list suspect))

(append {:name "Doogie Howser" :glitter-index 42} suspect-list)

;; Exercise 3.
(defn validate
  "so confused by this one"
  [keywords record]
  )


;; Exercise 4.
(defn glitter-list->csv
  [glitter-list]
  (clojure.string/join "\n" (map #(str (:name %) "," (:glitter-index %)) glitter-list)))

(defn glitter-list->csv
  [glitter-list]
  (->> glitter-list
       (map #(str (:name %)
                  ","
                  (:glitter-index %)))
       (clojure.string/join "\n")))


;; or 

(defn glitter-list->csv
  [glitter-list]
  (->> glitter-list
       (map vals)
       (map (partial clojure.string/join "," ))
       (clojure.string/join "\n")))

(glitter-list->csv tada)
