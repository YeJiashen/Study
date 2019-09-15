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

    int pos = list.Locate(7);
    cout << "Elem 7 is in location: " << pos << endl;

    list.~LinkList();
    return 0;
}