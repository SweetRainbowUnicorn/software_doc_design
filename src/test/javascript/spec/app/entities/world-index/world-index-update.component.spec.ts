import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { YahoofinanceTestModule } from '../../../test.module';
import { WorldIndexUpdateComponent } from 'app/entities/world-index/world-index-update.component';
import { WorldIndexService } from 'app/entities/world-index/world-index.service';
import { WorldIndex } from 'app/shared/model/world-index.model';

describe('Component Tests', () => {
  describe('WorldIndex Management Update Component', () => {
    let comp: WorldIndexUpdateComponent;
    let fixture: ComponentFixture<WorldIndexUpdateComponent>;
    let service: WorldIndexService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YahoofinanceTestModule],
        declarations: [WorldIndexUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(WorldIndexUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WorldIndexUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WorldIndexService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new WorldIndex(123);
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
        const entity = new WorldIndex();
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
