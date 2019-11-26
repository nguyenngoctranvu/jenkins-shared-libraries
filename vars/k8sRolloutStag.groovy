def call(project) {
    sh """kubectl -n oa-prod \
        rollout status deployment \
        ${project}-prod"""
}