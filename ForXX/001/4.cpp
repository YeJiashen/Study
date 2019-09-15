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

    int pos = list.Locate(7);
    cout << "Elem 7 is in location: " << pos << endl;

    list.~SqList();
    return 0;
}