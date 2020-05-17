import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YahoofinanceTestModule } from '../../../test.module';
import { YahooUserDetailComponent } from 'app/entities/yahoo-user/yahoo-user-detail.component';
import { YahooUser } from 'app/shared/model/yahoo-user.model';

describe('Component Tests', () => {
  describe('YahooUser Management Detail Component', () => {
    let comp: YahooUserDetailComponent;
    let fixture: ComponentFixture<YahooUserDetailComponent>;
    const route = ({ data: of({ yahooUser: new YahooUser(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YahoofinanceTestModule],
        declarations: [YahooUserDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(YahooUserDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(YahooUserDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load yahooUser on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.yahooUser).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
