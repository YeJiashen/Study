#include <iostream>
#include <stdio.h>
#include <stdlib.h>

#include "LinkList.h"

using namespace std;

template <class T>
LinkList<T>::LinkList()
{
    Head = new Node<T>;
    Head->next = NULL;
}

template <class T>
LinkList<T>::~LinkList()
{
    while(Head)
    {
        Node<T> *p;
        p = Head;
        Head = Head->next;
        delete p;
    }
    Head = NULL;
}

template <class T>
void LinkList<T>::CreateList(int n)
{
    Node<T> *p;
    p = Head;
    for (int i = 0; i < n; ++i)
    {
        cout << "Please input " << (i+1) << " elem: ";
        p->next = new Node<T>;
        p = p->next;
        cin>>p->data;
    }
    cout << "Create list finish;" << endl;
}

template <class T>
void LinkList<T>::Insert(int i, T e)
{
    ++i;
    Node<T> *p;
    p = Head;
    int j = 0;
    while(p && j<(i-1))
    {
        p = p->next;
        j++;
    }

    if (!p || j>(i-1))
    {
        throw "Location exception;";
    }
    else
    {
        Node<T> *s;
        s = new Node<T>;
        s->data = e;
        s->next = p->next;
        p->next = s;
    }
}

template <class T>
T LinkList<T>::Delete(int i)
{
    T x;
    Node<T> *p, *q;
    p = Head;
    int j = 0;
    while(p->next && j<(i-1)) 
    {
        p = p->next;
        j++;
    }

    if (!p->next || j>(i-1))
    {
        throw "Location exception;";
    }
    else
    {
        q = p->next;
        p->next = q->next;
        x = q->data;
        delete q;
        return x;
    }
}

template <class T>
T LinkList<T>::GetElem(int i)
{
    Node<T> *p;
    p = Head->next;
    int j = 1;
    while(p && j<i)
    {
        p = p->next;
        j++;
    }
    if (!p || j>i)
    {
        throw "Location exception;";
    }
    else 
    {
        return p->data;
    }
}

template <class T>
int LinkList<T>::Locate(T e)
{
    Node<T> *p;
    p = Head;
    int j = 0;
    while(p != NULL)
    {
        if (p->data == e)
        {
            return j;
        }
        p = p->next;
        j++;
    }

    return 0;
}

template <class T>
T LinkList<T>::Prior(T e)
{
    
}

template <class T>
int LinkList<T>::Empty()
{
    
}

template <class T>
int LinkList<T>::Length()
{
    
}

template <class T>
void LinkList<T>::Clear()
{
    
}

template <class T>
void LinkList<T>::ListDisp()
{
    Node<T> *p;
    p = Head;
    int j = 0;
    while(p != NULL)
    {
        if (p == Head)
        {
            cout << "I am Head;" << endl;
        }
        else 
        {
            cout << "Pos: " << j << " and data is: " << p->data << endl;
        }
        p = p->next;
        j++;
    }
}