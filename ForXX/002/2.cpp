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
    int i = list.Delete(3);
    cout << "Delete elem is: " << i << ";" << endl;

    list.ListDisp();
    list.~LinkList();
    return 0;
}