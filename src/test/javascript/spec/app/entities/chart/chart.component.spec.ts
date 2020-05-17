import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YahoofinanceTestModule } from '../../../test.module';
import { ChartComponent } from 'app/entities/chart/chart.component';
import { ChartService } from 'app/entities/chart/chart.service';
import { Chart } from 'app/shared/model/chart.model';

describe('Component Tests', () => {
  describe('Chart Management Component', () => {
    let comp: ChartComponent;
    let fixture: ComponentFixture<ChartComponent>;
    let service: ChartService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YahoofinanceTestModule],
        declarations: [ChartComponent]
      })
        .overrideTemplate(ChartComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ChartComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ChartService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Chart(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.charts && comp.charts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
