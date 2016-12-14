namespace scala jarta.thoughtservice.thriftscala

struct Thought {
    1: i32 id
    2: string title
}

service ThoughtService {
    Thought getThought(1: i32 id)

    Thought insertThought(1: Thought thought)

    void deleteThought(1: i32 id)
}