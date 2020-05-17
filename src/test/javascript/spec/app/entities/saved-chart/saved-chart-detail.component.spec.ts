import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YahoofinanceTestModule } from '../../../test.module';
import { SavedChartDetailComponent } from 'app/entities/saved-chart/saved-chart-detail.component';
import { SavedChart } from 'app/shared/model/saved-chart.model';

describe('Component Tests', () => {
  describe('SavedChart Management Detail Component', () => {
    let comp: SavedChartDetailComponent;
    let fixture: ComponentFixture<SavedChartDetailComponent>;
    const route = ({ data: of({ savedChart: new SavedChart(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YahoofinanceTestModule],
        declarations: [SavedChartDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SavedChartDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SavedChartDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load savedChart on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.savedChart).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
