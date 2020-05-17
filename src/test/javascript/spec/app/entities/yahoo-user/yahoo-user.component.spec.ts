import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YahoofinanceTestModule } from '../../../test.module';
import { YahooUserComponent } from 'app/entities/yahoo-user/yahoo-user.component';
import { YahooUserService } from 'app/entities/yahoo-user/yahoo-user.service';
import { YahooUser } from 'app/shared/model/yahoo-user.model';

describe('Component Tests', () => {
  describe('YahooUser Management Component', () => {
    let comp: YahooUserComponent;
    let fixture: ComponentFixture<YahooUserComponent>;
    let service: YahooUserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YahoofinanceTestModule],
        declarations: [YahooUserComponent]
      })
        .overrideTemplate(YahooUserComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(YahooUserComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(YahooUserService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new YahooUser(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.yahooUsers && comp.yahooUsers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
