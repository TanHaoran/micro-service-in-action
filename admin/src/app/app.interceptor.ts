import {Injectable} from '@angular/core';
import {HttpClient, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {tap} from 'rxjs/operators';
import {Observable} from 'rxjs';

@Injectable()
export class RefreshInterceptor implements HttpInterceptor {

    constructor(private http: HttpClient) {
    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(req).pipe(
            tap(() => {
                },
                error => {
                    console.log(error);
                    if (error.status === 500 && error.error.message === 'refresh fail') {
                        // 这里如果 refresh_token 失效的话，会跳转到登录服务器重新登录
                        this.logout();
                        window.location.href = 'http://auth.com:9090/oauth/authorize?' +
                            'client_id=admin&' +
                            'redirect_uri=http://admin.com:8080/oauth/callback&' +
                            'response_type=code&' +
                            'state=abc';
                    }
                }));
    }

    logout() {
        this.http.post('logout', {}).subscribe(() => {
            window.location.href = 'http://auth.com:9090/logout?redirect_uri=http://admin.com:8080';
        });
    }
}
