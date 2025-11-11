document.addEventListener('DOMContentLoaded', function () {
  // On change, update the URL with ?lang=en|es so LocaleChangeInterceptor applies
  document.querySelectorAll('.lang-select').forEach(function (sel) {
    sel.addEventListener('change', function () {
      var chosen = (sel.value || '').toUpperCase() === 'EN' ? 'en' : 'es';
      try {
        var url = new URL(window.location.href);
        url.searchParams.set('lang', chosen);
        window.location.href = url.toString();
      } catch (e) {
        // Fallback if URL API not available
        var sep = window.location.href.indexOf('?') === -1 ? '?' : '&';
        window.location.href = window.location.href + sep + 'lang=' + chosen;
      }
    });
  });
});
