template<class T>
class SqList
{
private:
    T *elem;
    int length;
    int listsize;

public:
    SqList(int m);
    ~SqList();
    void CreateList(int n);
    void Insert(int i, T e);
    T Delete(int i);
    T GetElem(int i);
    int Locate(T e);
    void Clear();
    int Empty();
    int Full();
    int Length();
    void ListDisp();
};