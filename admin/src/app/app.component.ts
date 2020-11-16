import {Component} from '@angular/core';

import {HttpClient} from '@angular/common/http';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent {
    title = 'admin';
    authenticated = false;
    credentials = {
        username: 'jerry',
        password: '123456'
    };
    order: any = {
        id: '',
        productId: ''
    };

    constructor(private http: HttpClient) {

    }

    login() {
        this.http.post('/login', this.credentials).subscribe(() => {
            this.authenticated = true;
        }, () => {
            alert('login fail');
        });
    }

    logout() {
        this.http.post('/logout', this.credentials).subscribe(() => {
            this.authenticated = false;
        }, () => {
            alert('logout fail');
        });
    }

    getOrder() {
        this.http.get('/api/order/orders/1').subscribe((data) => {
            this.order = data;
        }, () => {
            alert('get order fail');
        });
    }
}
