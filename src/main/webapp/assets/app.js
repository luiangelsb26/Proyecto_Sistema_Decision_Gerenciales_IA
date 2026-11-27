(()=>{'use strict';
const body=document.body;
document.querySelectorAll('.form-label:not([for])').forEach((label,index)=>{const field=label.parentElement.querySelector('input:not([type="hidden"]),select,textarea');if(field){if(!field.id)field.id='form-field-'+index;label.htmlFor=field.id;}});
document.querySelectorAll('.btn-close:not([aria-label])').forEach(button=>button.setAttribute('aria-label','Cerrar ventana'));
document.querySelectorAll('.texto-analisis,.seccion-analisis').forEach(element=>{if(element.childElementCount===0)element.textContent=element.textContent.trim();});
document.querySelectorAll('.badge').forEach(badge=>{const level=badge.textContent.trim().toLocaleUpperCase('es');const color=({'MUY BAJO':'bg-danger','BAJO':'bg-danger','MEDIO':'bg-warning','ALTO':'bg-success','MUY ALTO':'bg-success'})[level];if(color){badge.classList.remove('bg-primary','bg-secondary','bg-success','bg-warning','bg-danger','text-bg-primary','text-bg-secondary','text-bg-success','text-bg-warning','text-bg-danger','text-white','text-dark');badge.classList.add(color);}});
const toggle=document.querySelector('[data-sidebar-toggle]');
const overlay=document.querySelector('.nav-overlay');
const sidebar=document.querySelector('.sdg-sidebar');
const mobile=matchMedia('(max-width:800px)');
const syncSidebar=()=>{if(sidebar){const hidden=mobile.matches&&!body.classList.contains('nav-open');sidebar.inert=hidden;sidebar.setAttribute('aria-hidden',String(hidden));}};
const closeNav=()=>{body.classList.remove('nav-open');toggle?.setAttribute('aria-expanded','false');syncSidebar();};
toggle?.addEventListener('click',()=>{const opened=body.classList.toggle('nav-open');toggle.setAttribute('aria-expanded',String(opened));syncSidebar();});
mobile.addEventListener('change',closeNav);syncSidebar();
overlay?.addEventListener('click',closeNav);
document.addEventListener('keydown',event=>{if(event.key==='Escape')closeNav();});
document.querySelectorAll('.sdg-nav a').forEach(a=>a.addEventListener('click',closeNav));
document.querySelectorAll('table.table').forEach(table=>{if(!table.closest('.table-responsive')){const wrapper=document.createElement('div');wrapper.className='table-responsive';table.before(wrapper);wrapper.append(table);}});
document.querySelector('[data-password-toggle]')?.addEventListener('click',event=>{
const input=document.getElementById('password');const visible=input.type==='password';input.type=visible?'text':'password';
event.currentTarget.setAttribute('aria-label',visible?'Ocultar contraseña':'Mostrar contraseña');event.currentTarget.setAttribute('aria-pressed',String(visible));
});
const searchable=['empresas','evaluaciones','analisis_lista','resultados_lista','reportes'];
if(searchable.includes(body.dataset.page))document.querySelectorAll('.table-responsive').forEach((wrapper,index)=>{
const table=wrapper.querySelector('table');if(!table?.tBodies.length)return;
const rows=Array.from(table.tBodies[0].rows).filter(row=>row.cells.length>1);
const emptyRows=Array.from(table.tBodies[0].rows).filter(row=>row.cells.length===1);
const toolbar=document.createElement('div');toolbar.className='table-search';
const box=document.createElement('label');box.className='search-box';
const hidden=document.createElement('span');hidden.className='screen-reader-only';hidden.textContent='Buscar en la tabla '+(index+1);
const icon=document.createElementNS('http://www.w3.org/2000/svg','svg');icon.setAttribute('class','icon');icon.setAttribute('aria-hidden','true');
const use=document.createElementNS('http://www.w3.org/2000/svg','use');use.setAttribute('href','#i-search');icon.append(use);
const input=document.createElement('input');input.type='search';input.className='form-control';input.placeholder='Buscar en esta tabla…';
const count=document.createElement('span');count.className='table-count';count.setAttribute('aria-live','polite');
box.append(hidden,icon,input);toolbar.append(box,count);wrapper.before(toolbar);
const normalize=value=>value.normalize('NFD').replace(/[\u0300-\u036f]/g,'').toLocaleLowerCase('es');
const noResults=document.createElement('tr');const cell=document.createElement('td');cell.colSpan=table.tHead?.rows[0]?.cells.length||1;cell.className='text-center text-muted py-4';cell.textContent='No hay coincidencias para tu búsqueda.';noResults.append(cell);noResults.hidden=true;table.tBodies[0].append(noResults);
const update=()=>{const term=normalize(input.value.trim());let found=0;rows.forEach(row=>{row.hidden=!normalize(row.textContent).includes(term);if(!row.hidden)found++;});emptyRows.forEach(row=>row.hidden=Boolean(term));count.textContent=found+' '+(found===1?'registro':'registros');noResults.hidden=!term||found>0;};
input.addEventListener('input',update);update();
});
if(body.dataset.page==='cuestionario'){
const form=document.querySelector('form[action="EvaluacionRespuestaServlet"]');
if(form){const inputs=Array.from(form.querySelectorAll('input[type="radio"]'));const total=new Set(inputs.map(i=>i.name)).size;
const section=document.createElement('div');section.className='question-progress';const text=document.createElement('span');text.setAttribute('aria-live','polite');
const track=document.createElement('div');track.className='progress';track.setAttribute('role','progressbar');track.setAttribute('aria-label','Avance del cuestionario');track.setAttribute('aria-valuemin','0');track.setAttribute('aria-valuemax',String(total));
const bar=document.createElement('div');bar.className='progress-bar';track.append(bar);section.append(text,track);form.before(section);
const update=()=>{const completed=new Set(inputs.filter(i=>i.checked).map(i=>i.name)).size;text.textContent=completed+' de '+total+' preguntas respondidas';bar.style.width=(total?completed/total*100:0)+'%';track.setAttribute('aria-valuenow',String(completed));};form.addEventListener('change',update);update();}
}
})();
