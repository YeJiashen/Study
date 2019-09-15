#include <iostream>
#include <stdio.h>
#include <stdlib.h>

#include "SqList.h"

using namespace std;

template <class T>
SqList<T>::SqList(int m)
{
    elem = new T[m];
    if (!elem) throw "Memory allocation failed;";
    length = 0;
    listsize = m;
    cout << "Init success;" << endl;
}

template <class T>
SqList<T>::~SqList()
{
    delete [] elem;
    length = 0;
    listsize = 0;
    cout << "Delete Sq success;" << endl;
}

template <class T>
void SqList<T>::CreateList(int n) {
    // int n;
    // cout<<"Please input elem's count:";
    // cin>>n;
    for (int i = 0; i < n; ++i)
    {
        cout<<"Please input " << (i+1) << " elem: ";
        cin>>elem[i];
        length++;
    }
};

template <class T>
void SqList<T>::Insert(int i, T e) {
    if(length >= listsize) throw "Overflow;";

    if(i < 1 || i > length + 1) throw "Insert error;";

    for (int j = length; j >= i; j--)
    {
        elem[j] = elem[j-1];
    }

    elem[i - 1] = e;

    ++length;
    cout << "Insert success;" << endl;
}

template <class T>
T SqList<T>::Delete(int i) {
    if (length  == 0) throw "Underflow;";
    if(i < 1 || (i > length + 1)) throw "Delete error;";
 
    T e = elem[i - 1];
    for (int j = i; j < length; j++)
    {
        elem[j-1] = elem[j];
    }
 
    --length;
    cout << "Delete success;" << endl;
    return e;
}

template <class T>
T SqList<T>::GetElem(int i) {};

template <class T>
int SqList<T>::Locate(T e) {
    for (int i = 0; i < length; ++i)
    {
        if (elem[i] == e)
        {
            return i+1;
        }
    }
    return 0;
}

template <class T>
void SqList<T>::Clear(){};

template <class T>
int SqList<T>::Empty(){};

template <class T>
int SqList<T>::Full(){};

template <class T>
int SqList<T>::Length(){};

template <class T>
void SqList<T>::ListDisp(){
    cout << "Current SqList is: " << endl;
    for(int i=0;i<length;i++){
        cout<< i <<" : "<< elem[i] <<"\r\n";
    }
    cout<<endl;
};