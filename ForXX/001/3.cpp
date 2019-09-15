#include <iostream>
#include "SqList.cpp"

using namespace std;

int main()
{

    int n;
    cout<<"Please input list max size:";
    cin>>n;

    SqList<int> list(n);
    
    cout<<"Please input list size:";
    cin>>n;
    list.CreateList(n);

    list.Insert(4, 20);

    list.ListDisp();

    list.~SqList();
    return 0;
}