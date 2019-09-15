template<class T>
struct Node
{
    T data;
    Node * next = NULL;
};

template<class T>
class LinkList
{
private:
    Node<T> * Head;

public:
    LinkList();
    ~LinkList();
    void CreateList(int n);
    void Insert(int i, T e);
    T Delete(int i);
    T GetElem(int i);
    int Locate(T e);
    T Prior(T e);
    int Empty();
    int Length();
    void Clear();
    void ListDisp();
};