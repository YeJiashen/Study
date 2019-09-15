#include <iostream>
#include "LinkList.cpp"

using namespace std;

int main()
{
    LinkList<int> list;
    
    cout<<"Please input list size:";
    int n;
    cin>>n;
    list.CreateList(n);
    list.Insert(4, 20);

    list.ListDisp();
    list.~LinkList();
    return 0;
}