#include<bits/stdc++.h>
using namespace std;

int main(){
	vector<int> alpha = {1,2,4,5,7,8,10,11,13,14,16,17,19, 20, 22,23,25,26};

	for(int i=0; i<alpha.size(); i++){
		cout << "inverse alpha of: " << alpha[i]<< endl;

		for(int j=0; j<alpha.size(); j++){
			int mod = (alpha[i]*alpha[j]) % 27;
			cout << alpha[i] <<" * " << alpha[j] << " % 27 = " << mod << endl;
			if(mod == 1) break; 
		} cout << endl;

	}
	return 0;
}