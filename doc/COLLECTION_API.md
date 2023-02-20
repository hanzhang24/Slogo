# Collections API Lab Discussion
### NAMES Aryan, Yegor, Alec, Jack, Han
### TEAM 7



## In your experience using these collections, are they hard or easy to use?
* I find that they are relatively easy to use once you have a basic understanding of the purpose of each collection (lists, sets, maps, etc).
* Without a proper understanding, it would be difficult to understand how each method should be used

## In your experience using these collections, do you feel mistakes are easy to avoid?
* So long as a programmer understands how each specific collection is meant to work, the methods are intuitive to call
* The exception is Maps, which can sometimes be confusing to use. For example, to increment a counter, the call is like map.put(key, map.get(key) + 1)

## What methods are common to all collections (except Maps)?
* add()
* clear()
* isEmpty()
* size()
* toArray()

## What methods are common to all Deques?
* Add(E)
* addFirst()
* addLast()
* Contains()
* descendingIterator()
* Element()
* getFirst()
* getLast()
* iterator()
* offer()
* offerFirst()
* offerLast()
* peek()

## What is the purpose of each interface implemented by LinkedList?
* List interface: A linked list is a viable option for implementing a list
* AbstractCollections: Abstraction of Collections
  Deque: A linked list is a viable option for implementing a queue or stack

## How many different implementations are there for a Set?
* Three implementations for a Set – HashSet, TreeSet, LinkedHashSet


## What is the purpose of each superclass of PriorityQueue?
Object - provides the base for every class (OOP)
AbstractCollection - provides basic collection functions like iterators and sizes
AbstractQueue - provides basic queue operations like add and remove


## What is the purpose of the Collections utility class?
The Collections utility class consists exclusively of static methods that operate on or return collections


## API Characterics applied to Collections API

* Easy to learn 7/10

* Encourages extension 10/10

* Leads to readable code 7/10

* Hard to misuse 5/10