package subway.domain;

import java.util.Optional;
import java.util.function.Predicate;

public class SectionAddManager {

    private final Sections sections;

    public SectionAddManager(final Sections sections) {
        this.sections = sections;
    }

    public void validate(final Station upStation, final Station downStation, final int distance) {
        validateLineHasOneOf(upStation, downStation);

        sections.filter(matchOneOf(upStation, downStation))
            .ifPresent(section -> validateDistance(section, distance));
    }

    private void validateDistance(final Section section, final int distance) {
        if (section.isNotLongerThan(distance)) {
            throw new IllegalArgumentException("추가할 구간의 크기가 너무 큽니다.");
        }
    }

    public Optional<Section> lookForChange(Section newSection) {
        return sections.filter(section -> section.matchEitherStation(newSection))
            .map(section -> section.cutBy(newSection));
    }

    private Predicate<Section> matchOneOf(final Station upStation, final Station downStation) {
        return section -> section.hasUpStation(upStation) || section.hasDownStation(downStation);
    }

    private void validateLineHasOneOf(final Station upStation, final Station downStation) {
        boolean hasUpStation = sections.hasStation(upStation);
        boolean hasDownStation = sections.hasStation(downStation);

        validateBoth(hasUpStation, hasDownStation);
        validateNotBoth(hasUpStation, hasDownStation);
    }

    private void validateBoth(final boolean hasUpStation, final boolean hasDownStation) {
        if (hasUpStation && hasDownStation) {
            throw new IllegalArgumentException("두 역이 모두 노선에 포함되어 있습니다.");
        }
    }

    private void validateNotBoth(final boolean hasUpStation, final boolean hasDownStation) {
        if (!hasUpStation && !hasDownStation) {
            throw new IllegalArgumentException("두 역이 모두 노선에 포함되어 있지 않습니다.");
        }
    }
}
