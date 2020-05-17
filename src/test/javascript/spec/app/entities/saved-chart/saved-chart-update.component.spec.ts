import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { YahoofinanceTestModule } from '../../../test.module';
import { SavedChartUpdateComponent } from 'app/entities/saved-chart/saved-chart-update.component';
import { SavedChartService } from 'app/entities/saved-chart/saved-chart.service';
import { SavedChart } from 'app/shared/model/saved-chart.model';

describe('Component Tests', () => {
  describe('SavedChart Management Update Component', () => {
    let comp: SavedChartUpdateComponent;
    let fixture: ComponentFixture<SavedChartUpdateComponent>;
    let service: SavedChartService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YahoofinanceTestModule],
        declarations: [SavedChartUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SavedChartUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SavedChartUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SavedChartService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SavedChart(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new SavedChart();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
