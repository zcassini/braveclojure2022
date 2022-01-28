(ns clojure-noob.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(def asym-hobbit-body-parts [{:name "head" :size 3}
                             {:name "left-eye" :size 1}
                             {:name "left-ear" :size 1}
                             {:name "mouth" :size 1}
                             {:name "nose" :size 1}
                             {:name "neck" :size 2}
                             {:name "left-shoulder" :size 3}
                             {:name "left-upper-arm" :size 3}
                             {:name "chest" :size 10}
                             {:name "back" :size 10}
                             {:name "left-forearm" :size 3}
                             {:name "abdomen" :size 6}
                             {:name "left-kidney" :size 1}
                             {:name "left-hand" :size 2}
                             {:name "left-knee" :size 2}
                             {:name "left-thigh" :size 4}
                             {:name "left-lower-leg" :size 3}
                             {:name "left-achilles" :size 1}
                             {:name "left-foot" :size 2}])

(defn symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (loop [remaining-asym-parts asym-body-parts
         final-body-parts []]
    (if (empty? remaining-asym-parts)
      final-body-parts
      (let [[part & remaining] remaining-asym-parts]
        (recur remaining
               (into final-body-parts
                     (set [part (matching-part part)])))))))

(defn better-symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (set [part (matching-part part)])))
          []
          asym-body-parts))

(defn hit
  [asym-body-parts]
  (let [sym-parts (better-symmetrize-body-parts asym-body-parts)
        body-part-size-sum (reduce + (map :size sym-parts))
        target (rand body-part-size-sum)]
    (loop [[part & remaining] sym-parts
            accumulated-size (:size part)]
       (if (> accumulated-size target)
         part
         (recur remaining (+ accumulated-size (:size (first remaining))))))))


;; Exercise 1
(str "Hello " "Nurse")
(vector 1 2 3 4)
(list 1 2  3 4)
(hash-map :a 1 :b 2 :c 3)
(hash-set 1 1 1 2 2 3 3 3 3 -5)

;; Exercise 2
(defn hundred [n] (+ n 100))
(hundred 1)

;; Exercise 3
(defn dec-maker [n] #(- % n))
(def dec9 (dec-maker 9))
(dec9 1)

;; Exercise 4
(defn mapset
  [f xs]
  (apply hash-set (map f xs)))
(mapset inc [1 1 2 2])

;; Exercise 5 & 6

(defn multiply-part [n part]
  (let [clean-part (last (clojure.string/split (:name part) #"-"))]
    (if (= clean-part (:name part))
      [ {:name clean-part :size (:size part) :quanity 1}]
      [ {:name clean-part :size (:size part) :quanity n}])))

(defn symmetrize-body-parts
  [n asym-body-parts]
  (map #(multiply-part n %) asym-body-parts))

(def five-parter #(symmetrize-body-parts 5 %))

(five-parter asym-hobbit-body-parts)



