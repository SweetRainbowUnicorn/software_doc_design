import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YahoofinanceTestModule } from '../../../test.module';
import { SavedChartComponent } from 'app/entities/saved-chart/saved-chart.component';
import { SavedChartService } from 'app/entities/saved-chart/saved-chart.service';
import { SavedChart } from 'app/shared/model/saved-chart.model';

describe('Component Tests', () => {
  describe('SavedChart Management Component', () => {
    let comp: SavedChartComponent;
    let fixture: ComponentFixture<SavedChartComponent>;
    let service: SavedChartService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YahoofinanceTestModule],
        declarations: [SavedChartComponent]
      })
        .overrideTemplate(SavedChartComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SavedChartComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SavedChartService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SavedChart(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.savedCharts && comp.savedCharts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
